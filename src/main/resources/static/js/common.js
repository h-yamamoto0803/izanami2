
const Traemon = (() => {
  const KEY = {
    session: "traemon_session",
    posts: "traemon_posts",
    notifications: "traemon_notifications",
    postDraft: "traemon_post_draft",
    accountDraft: "traemon_account_draft",
    accounts: "traemon_accounts"
  };

  const seedPosts = [
    {
      id:1,user:"山田工房",role:"craftsman",
      body:"暮らしに馴染む一点ものの器を制作しています。色やサイズの相談もできます。",
      tags:["陶芸","器"],likes:12,likedBy:[],consideredBy:[],
      owner:"craftsman@example.com",createdAt:"2026/08/18 09:30"
    },
    {
      id:2,user:"木工 佐藤",role:"craftsman",
      body:"国産材を使った家具を制作しています。「こんな家具が欲しい」という声を募集中です。",
      tags:["木工","家具"],likes:8,likedBy:[],consideredBy:[],
      owner:"wood@example.com",createdAt:"2026/08/17 16:20"
    },
    {
      id:3,user:"伝統工芸が好き",role:"consumer",
      body:"玄関に置ける小さな花器を探しています。落ち着いた色味で、長く使えるものが希望です。",
      tags:["陶芸","花器"],likes:21,likedBy:[],consideredBy:[],
      owner:"user@example.com",createdAt:"2026/08/17 10:10"
    },
    {
      id:4,user:"漆工房",role:"craftsman",
      body:"漆塗りの小物を制作しています。名入れにも対応できます。",
      tags:["漆","小物"],likes:17,likedBy:[],consideredBy:[],
      owner:"urushi@example.com",createdAt:"2026/08/16 13:00"
    },
    {
      id:5,user:"暮らしを整えたい",role:"consumer",
      body:"職人さんに、長く使える木製のカトラリーを作ってほしいです。",
      tags:["木工","カトラリー"],likes:6,likedBy:[],consideredBy:[],
      owner:"consumer2@example.com",createdAt:"2026/08/15 18:40"
    }
  ];

  // 職人ごとの専門タグ。将来的にはDBから取得する想定です。
  const craftsmanProfiles = {
    "craftsman@example.com": { displayName:"山田工房", specialtyTags:["陶芸","器"] },
    "wood@example.com": { displayName:"木工 佐藤", specialtyTags:["木工","家具"] },
    "urushi@example.com": { displayName:"漆工房", specialtyTags:["漆","小物"] }
  };

  function getCraftsmanProfile(email){
    return craftsmanProfiles[email] || {
      displayName:"職人ユーザー",
      specialtyTags:["陶芸"]
    };
  }

  const $ = (selector, root=document) => root.querySelector(selector);
  const $$ = (selector, root=document) => [...root.querySelectorAll(selector)];

  function escapeHtml(value=""){
    return String(value).replace(/[&<>"']/g, ch => ({
      "&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#039;"
    }[ch]));
  }

  function getAccounts(){
    return JSON.parse(localStorage.getItem(KEY.accounts) || "[]");
  }

  function saveAccounts(accounts){
    localStorage.setItem(KEY.accounts, JSON.stringify(accounts));
  }

  function seed(){
    if(!localStorage.getItem(KEY.accounts)){
      saveAccounts([]);
    }
    if(!localStorage.getItem(KEY.posts)){
      localStorage.setItem(KEY.posts, JSON.stringify(seedPosts));
    }
    if(!localStorage.getItem(KEY.notifications)){
      localStorage.setItem(KEY.notifications, JSON.stringify([
        {text:"Traemonへようこそ。投稿をチェックしてみましょう。",date:"2026/08/18"}
      ]));
    }
  }

  function getSession(){
    return JSON.parse(localStorage.getItem(KEY.session) || "null");
  }

  function setSession(session){
    localStorage.setItem(KEY.session, JSON.stringify(session));
  }

  function logout(){
    localStorage.removeItem(KEY.session);
    location.href = "menu.html";
  }

  function role(){
    return getSession()?.role || "guest";
  }

  function roleLabel(){
    return role()==="craftsman" ? "職人" : role()==="consumer" ? "ユーザー" : "ゲスト";
  }

  function homeByRole(){
    return role()==="craftsman" ? "craftsman-menu.html"
         : role()==="consumer" ? "consumer-menu.html"
         : "menu.html";
  }

  function requireLogin(expectedRole=null){
    const s=getSession();
    if(!s){
      location.href=expectedRole==="craftsman" ? "craftsman-login.html" : "consumer-login.html";
      return false;
    }
    if(expectedRole && s.role!==expectedRole){
      location.href=s.role==="craftsman" ? "craftsman-menu.html" : "consumer-menu.html";
      return false;
    }
    return true;
  }

  function login(loginRole,email,password){
    if(!email || !password){
      return {ok:false,message:"メールアドレスとパスワードを入力してください。"};
    }
    seed();
    const profile = loginRole==="craftsman" ? getCraftsmanProfile(email) : null;
    const account = loginRole==="consumer" ? getAccounts().find(a=>a.email===email) : null;

    if(loginRole==="consumer" && !account){
      return {ok:false,message:"登録されていないメールアドレスです。新規登録してください。"};
    }
    if(loginRole==="consumer" && account.password !== password){
      return {ok:false,message:"メールアドレスまたはパスワードが正しくありません。"};
    }

    setSession({
      role:loginRole,
      email,
      displayName:loginRole==="craftsman" ? profile.displayName : account.name,
      specialtyTags:loginRole==="craftsman" ? profile.specialtyTags : []
    });
    addNotification("ログインしました。");
    return {ok:true};
  }

  function getPosts(){
    seed();
    return JSON.parse(localStorage.getItem(KEY.posts) || "[]");
  }

  function savePosts(posts){
    localStorage.setItem(KEY.posts,JSON.stringify(posts));
  }

  function getNotifications(){
    seed();
    return JSON.parse(localStorage.getItem(KEY.notifications) || "[]");
  }

  function addNotification(text){
    const list=getNotifications();
    list.unshift({
      text,
      date:new Date().toLocaleString("ja-JP",{year:"numeric",month:"2-digit",day:"2-digit"})
    });
    localStorage.setItem(KEY.notifications,JSON.stringify(list.slice(0,30)));
  }

  function renderHeader(target="#header"){
    const el=$(target);
    if(!el) return;

    const r=role();
    if(r==="guest"){
      el.innerHTML=`
        <header class="site-header guest-header">
          <div class="container header-inner">
            <a class="logo" href="menu.html">Traemon</a>
            <div class="header-right">
              <span class="role-badge guest"><span class="role-dot"></span>ゲスト</span>
              <a class="header-post guest-post" href="post-create.html">＋ 投稿する</a>
              <a class="header-login craftsman-login" href="craftsman-login.html">職人用ログイン</a>
              <a class="header-login consumer-login" href="consumer-login.html">ユーザー用ログイン</a>
            </div>
          </div>
        </header>`;
      return;
    }

    const isCraftsman=r==="craftsman";
    const home=isCraftsman?"craftsman-menu.html":"consumer-menu.html";
    const account=isCraftsman?"craftsman-account.html":"consumer-account.html";
    el.innerHTML=`
      <header class="site-header ${isCraftsman?"craftsman-header":"consumer-header"}">
        <div class="container header-inner">
          <a class="logo" href="${home}">Traemon</a>
          <div class="header-right">
            <span class="role-badge ${isCraftsman?"craftsman":"consumer"}"><span class="role-dot"></span>${isCraftsman?"職人":"ユーザー"}</span>
            <a class="header-post ${isCraftsman?"craftsman-post":"consumer-post"}" href="post-create.html">＋ 投稿する</a>
            <div class="notification-wrap">
              <button class="icon-btn" id="notificationButton" type="button" aria-label="通知">🔔</button>
              <div class="notification-panel" id="notificationPanel"></div>
            </div>
            <a class="account-btn" href="${account}">アカウント</a>
            <button class="logout-btn" id="logoutButton" type="button">ログアウト</button>
          </div>
        </div>
      </header>`;

    const button=$("#notificationButton");
    const panel=$("#notificationPanel");
    if(button && panel){
      panel.innerHTML=getNotifications().map(n=>`
        <div class="notification-item">
          <div>${escapeHtml(n.text)}</div>
          <small>${escapeHtml(n.date)}</small>
        </div>`).join("") || `<div class="empty">通知はありません。</div>`;
      button.addEventListener("click",()=>panel.classList.toggle("open"));
      document.addEventListener("click",e=>{
        if(!e.target.closest(".notification-wrap")) panel.classList.remove("open");
      });
    }
    $("#logoutButton")?.addEventListener("click",()=>logout());
  }

  function renderTimeline(target="#postList",options={}){
    const el=$(target);
    if(!el) return;

    let posts=getPosts();
    const s=getSession();
    const query=(options.query ?? $("#searchInput")?.value ?? "").trim().toLowerCase();
    const tag=options.tag ?? $("#tagFilter")?.value ?? "";
    // 並び順は現状「いいね順」をデフォルトにしています。
    // 将来のUI復活用に「new（新着順）」もコード上は残しています。
    const sort=options.sort ?? $("#sortSelect")?.value ?? "likes";

    // 職人専用画面では、ログイン中の職人が持つ専門タグと
    // 投稿タグが1つでも一致する投稿だけを表示します。
    if(options.craftsmanOnly){
      const specialtyTags=s?.specialtyTags || getCraftsmanProfile(s?.email || "").specialtyTags;
      posts=posts.filter(p=>p.tags.some(t=>specialtyTags.includes(t)));
    }
    if(query){
      posts=posts.filter(p=>
        `${p.user} ${p.body} ${p.tags.join(" ")}`.toLowerCase().includes(query)
      );
    }
    if(tag) posts=posts.filter(p=>p.tags.includes(tag));
    if(sort==="likes") posts.sort((a,b)=>b.likes-a.likes);
    else posts.sort((a,b)=>String(b.createdAt).localeCompare(String(a.createdAt)));

    if(!posts.length){
      el.innerHTML=`<div class="card empty">条件に一致する投稿がありません。</div>`;
      return;
    }

    el.innerHTML=posts.map(post=>{
      const liked=!!s && (post.likedBy||[]).includes(s.email);
      const considered=!!s && (post.consideredBy||[]).includes(s.email);
      const action=options.craftsmanOnly
        ? `<button class="btn btn-outline btn-consider ${considered?"active":""}" data-consider="${post.id}">
             ${considered?"✓ 検討中":"検討中"}
           </button>`
        : `<button class="btn btn-outline btn-like ${liked?"active":""}" data-like="${post.id}">
             ♥ ${liked?"いいね済み":"いいね"} ${post.likes}
           </button>`;

      return `
        <article class="card post-card">
          <div class="post-top">
            <div>
              <div class="post-user">${escapeHtml(post.user)}</div>
              <div class="post-date">${escapeHtml(post.createdAt)}</div>
            </div>
            <span class="role-badge ${post.role==="craftsman"?"craftsman":"consumer"}">
              ${post.role==="craftsman"?"職人":"ユーザー"}
            </span>
          </div>
          <div class="post-body">${escapeHtml(post.body)}</div>
          <div class="post-tags">
            ${post.tags.map(t=>`<span class="tag">#${escapeHtml(t)}</span>`).join("")}
          </div>
          <div class="post-actions">
            <span style="color:var(--muted);font-size:12px">
              ${post.role==="consumer"?"職人への需要":"作品・サービス紹介"}
            </span>
            <div>${action}</div>
          </div>
        </article>`;
    }).join("");

    $$("[data-like]",el).forEach(btn=>{
      btn.addEventListener("click",()=>toggleLike(Number(btn.dataset.like),options));
    });
    $$("[data-consider]",el).forEach(btn=>{
      btn.addEventListener("click",()=>toggleConsider(Number(btn.dataset.consider),options));
    });
  }

  function toggleLike(id,options={}){
    const s=getSession();
    if(!s){
      location.href="consumer-login.html";
      return;
    }
    if(s.role!=="consumer"){
      alert("いいねはユーザーログイン時に利用できます。");
      return;
    }
    const posts=getPosts();
    const post=posts.find(p=>p.id===id);
    if(!post) return;
    post.likedBy=post.likedBy||[];
    const i=post.likedBy.indexOf(s.email);
    if(i>=0){
      post.likedBy.splice(i,1);
      post.likes=Math.max(0,post.likes-1);
    }else{
      post.likedBy.push(s.email);
      post.likes++;
      addNotification(`「${post.user}」の投稿にいいねしました。`);
    }
    savePosts(posts);
    renderTimeline("#postList",options);
  }

  function toggleConsider(id,options={}){
    const s=getSession();
    if(!s){
      location.href="craftsman-login.html";
      return;
    }
    if(s.role!=="craftsman"){
      alert("検討中は職人ログイン時に利用できます。");
      return;
    }
    const posts=getPosts();
    const post=posts.find(p=>p.id===id);
    if(!post) return;
    post.consideredBy=post.consideredBy||[];
    const i=post.consideredBy.indexOf(s.email);
    if(i>=0) post.consideredBy.splice(i,1);
    else{
      post.consideredBy.push(s.email);
      addNotification(`「${post.user}」の投稿を検討中にしました。`);
    }
    savePosts(posts);
    renderTimeline("#postList",options);
  }

  function populateTags(options={}){
    const el=$("#tagFilter");
    if(!el) return;

    let tags;
    if(options.craftsmanOnly){
      const s=getSession();
      const specialtyTags=s?.specialtyTags || getCraftsmanProfile(s?.email || "").specialtyTags;
      tags=specialtyTags;
    }else{
      tags=[...new Set(getPosts().flatMap(p=>p.tags))].sort();
    }

    el.innerHTML=`<option value="">すべてのタグ</option>`+
      tags.map(t=>`<option value="${escapeHtml(t)}">${escapeHtml(t)}</option>`).join("");
  }

  function setupTimeline(options={}){
    seed();
    if(!options.noTagFilter) populateTags(options);
    renderTimeline("#postList",options);
    $("#searchInput")?.addEventListener("input",()=>renderTimeline("#postList",options));
    if(!options.noTagFilter){
      $("#tagFilter")?.addEventListener("change",()=>renderTimeline("#postList",options));
    }
    // 現在は並び順UIを非表示にしていますが、将来復活できるよう処理は残しています。
    $("#sortSelect")?.addEventListener("change",()=>renderTimeline("#postList",options));
  }

  function savePostDraft(form){
    const data=Object.fromEntries(new FormData(form).entries());
    const s=getSession();
    // 投稿者名はフォーム入力値ではなく、ログイン中アカウントの登録名を自動反映します。
    data.name=s?.displayName || (s?.role==="craftsman" ? "職人ユーザー" : "ユーザー");
    data.tags=(data.tags||"").split(/[、,\s]+/).map(s=>s.trim()).filter(Boolean);
    sessionStorage.setItem(KEY.postDraft,JSON.stringify(data));
  }

  function getPostDraft(){
    return JSON.parse(sessionStorage.getItem(KEY.postDraft)||"null");
  }

  function createPost(){
    const d=getPostDraft();
    const s=getSession();
    if(!d || !s) return false;
    const posts=getPosts();
    posts.unshift({
      id:Date.now(),
      user:s.displayName || (s.role==="craftsman"?"職人ユーザー":"ユーザー"),
      role:s.role,
      body:d.body,
      tags:d.tags||[],
      likes:0,
      likedBy:[],
      consideredBy:[],
      owner:s.email,
      createdAt:new Date().toLocaleString("ja-JP",{
        year:"numeric",month:"2-digit",day:"2-digit",hour:"2-digit",minute:"2-digit"
      })
    });
    savePosts(posts);
    addNotification("投稿を公開しました。");
    sessionStorage.removeItem(KEY.postDraft);
    return true;
  }

  function saveAccountDraft(form){
    const d=Object.fromEntries(new FormData(form).entries());
    sessionStorage.setItem(KEY.accountDraft,JSON.stringify(d));
  }

  function registerAccount(){
    const d=getAccountDraft();
    if(!d) return false;
    const accounts=getAccounts();
    const existing=accounts.find(a=>a.email===d.email);
    if(existing){ return false; }
    accounts.push({name:d.name,email:d.email,password:d.password});
    saveAccounts(accounts);
    setSession({role:"consumer",email:d.email,displayName:d.name || "ユーザー"});
    sessionStorage.removeItem(KEY.accountDraft);
    addNotification("ユーザーアカウントを登録しました。");
    return true;
  }

  function getAccountDraft(){
    return JSON.parse(sessionStorage.getItem(KEY.accountDraft)||"null");
  }

  function updateAccount(){
    const d=getAccountDraft();
    const s=getSession();
    if(!d || !s) return false;

    const oldEmail=s.email;
    s.email=d.email;
    s.displayName=d.name || s.displayName || "ユーザー";

    const accounts=getAccounts();
    const account=accounts.find(a=>a.email===oldEmail);
    if(account){
      account.email=s.email;
      account.name=s.displayName;
      account.password=d.password;
      saveAccounts(accounts);
    }
    setSession(s);

    const posts=getPosts();
    posts.forEach(post=>{
      if(post.owner===oldEmail){
        post.owner=s.email;
        post.user=s.displayName;
      }
    });
    savePosts(posts);

    sessionStorage.removeItem(KEY.accountDraft);
    addNotification("アカウント情報を更新しました。");
    return true;
  }

  function getMyPosts(){
    const s=getSession();
    return s ? getPosts().filter(p=>p.owner===s.email) : [];
  }

  function renderMyPosts(target="#myPosts"){
    const el=$(target);
    if(!el) return;
    const posts=getMyPosts();
    if(!posts.length){
      el.innerHTML=`
        <div class="card empty">
          まだ投稿がありません。<br>
          <a class="btn btn-primary" style="margin-top:12px" href="post-create.html">投稿を作成する</a>
        </div>`;
      return;
    }
    el.innerHTML=posts.map(p=>`
      <article class="card post-card">
        <div class="post-top">
          <strong>${escapeHtml(p.user)}</strong>
          <span class="post-date">${escapeHtml(p.createdAt)}</span>
        </div>
        <div class="post-body">${escapeHtml(p.body)}</div>
        <div class="post-tags">${p.tags.map(t=>`<span class="tag">#${escapeHtml(t)}</span>`).join("")}</div>
        <div class="post-actions"><span style="font-size:12px;color:var(--muted)">♥ ${p.likes} いいね</span></div>
      </article>
    `).join("");
  }

  return {
    $, $$, escapeHtml, seed, getSession, setSession, logout, role, roleLabel, getCraftsmanProfile,
    homeByRole, requireLogin, login, getPosts, savePosts, getNotifications,
    addNotification, renderHeader, renderTimeline, toggleLike, toggleConsider,
    populateTags, setupTimeline, savePostDraft, getPostDraft, createPost,
    saveAccountDraft, getAccountDraft, registerAccount, updateAccount, getMyPosts, renderMyPosts
  };
})();

document.addEventListener("DOMContentLoaded",()=>{
  Traemon.seed();
  Traemon.renderHeader();
});
