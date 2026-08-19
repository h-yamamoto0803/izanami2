
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
      id:1,title:"暮らしに馴染む一点ものの器",user:"山田工房",role:"craftsman",
      body:"暮らしに馴染む一点ものの器を制作しています。色やサイズの相談もできます。",
      tags:["陶芸","器"],likes:12,likedBy:[],consideredBy:[],
      owner:"craftsman@example.com",createdAt:"2026/08/18 09:30"
    },
    {
      id:2,title:"国産材でつくるオーダー家具",user:"木工 佐藤",role:"craftsman",
      body:"国産材を使った家具を制作しています。「こんな家具が欲しい」という声を募集中です。",
      tags:["木工","家具"],likes:8,likedBy:[],consideredBy:[],
      owner:"wood@example.com",createdAt:"2026/08/17 16:20"
    },
    {
      id:3,title:"玄関に置ける小さな花器が欲しい",user:"伝統工芸が好き",role:"consumer",
      body:"玄関に置ける小さな花器を探しています。落ち着いた色味で、長く使えるものが希望です。",
      tags:["陶芸","花器"],likes:21,likedBy:[],consideredBy:[],
      owner:"user@example.com",createdAt:"2026/08/17 10:10"
    },
    {
      id:4,title:"名入れ対応の漆塗り小物",user:"漆工房",role:"craftsman",
      body:"漆塗りの小物を制作しています。名入れにも対応できます。",
      tags:["漆","小物"],likes:17,likedBy:[],consideredBy:[],
      owner:"urushi@example.com",createdAt:"2026/08/16 13:00"
    },
    {
      id:5,title:"長く使える木製カトラリーを作ってほしい",user:"暮らしを整えたい",role:"consumer",
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
    } else {
      // 旧バージョンのデモデータにタイトルがない場合の移行処理
      const titleMap={
        1:"暮らしに馴染む一点ものの器",
        2:"国産材でつくるオーダー家具",
        3:"玄関に置ける小さな花器が欲しい",
        4:"名入れ対応の漆塗り小物",
        5:"長く使える木製カトラリーを作ってほしい"
      };
      const current=JSON.parse(localStorage.getItem(KEY.posts) || "[]");
      let changed=false;
      current.forEach(post=>{
        if(!post.title){
          post.title=titleMap[post.id] || "無題の投稿";
          changed=true;
        }
      });
      if(changed) localStorage.setItem(KEY.posts,JSON.stringify(current));
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
    return role()==="craftsman" ? "artisan-menu.html"
         : role()==="consumer" ? "customer-menu.html"
         : "menu.html";
  }

  function requireLogin(expectedRole=null){
    const s=getSession();
    if(!s){
      location.href=expectedRole==="craftsman" ? "artisan-login.html" : "customer-login.html";
      return false;
    }
    if(expectedRole && s.role!==expectedRole){
      location.href=s.role==="craftsman" ? "artisan-menu.html" : "customer-menu.html";
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
              <a class="header-login artisan-login" href="artisan-login.html">職人用ログイン</a>
              <a class="header-login customer-login" href="customer-login.html">ユーザー用ログイン</a>
            </div>
          </div>
        </header>`;
      return;
    }

    const isCraftsman=r==="craftsman";
    const home=isCraftsman?"artisan-menu.html":"customer-menu.html";
    const account=isCraftsman?"artisan-account.html":"customer-account.html";
    el.innerHTML=`
      <header class="site-header ${isCraftsman?"artisan-header":"customer-header"}">
        <div class="container header-inner">
          <a class="logo" href="${home}">Traemon</a>
          <div class="header-right">
            <span class="role-badge ${isCraftsman?"craftsman":"consumer"}"><span class="role-dot"></span>${isCraftsman?"職人":"ユーザー"}</span>
            <a class="header-post ${isCraftsman?"artisan-post":"customer-post"}" href="post-create.html">＋ 投稿する</a>
            <div class="notification-wrap">
              <button class="icon-btn" id="notificationButton" type="button" aria-label="通知">🔔</button>
              <div class="notification-panel" id="notificationPanel"></div>
            </div>
            <a class="account-btn" href="${account}">${escapeHtml(getSession()?.displayName || (isCraftsman ? "職人ユーザー" : "ユーザー"))}</a>
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
    const tag=options.tag ?? $("#tagFilter")?.value ?? "";

    // 職人専用画面では、ログイン中の職人が持つ専門タグと
    // 投稿タグが1つでも一致する投稿だけを表示します。
    if(options.craftsmanOnly){
      const specialtyTags=s?.specialtyTags || getCraftsmanProfile(s?.email || "").specialtyTags;
      posts=posts.filter(p=>p.tags.some(t=>specialtyTags.includes(t)));
    }

    if(tag) posts=posts.filter(p=>p.tags.includes(tag));

    // 投稿一覧は常に「いいね順」をデフォルトにします。
    posts.sort((a,b)=>b.likes-a.likes);

    if(!posts.length){
      el.innerHTML=`<div class="card empty">条件に一致する投稿がありません。</div>`;
      return;
    }

    el.innerHTML=posts.map(post=>{
      const liked=!!s && (post.likedBy||[]).includes(s.email);
      const considered=!!s && (post.consideredBy||[]).includes(s.email);
      const action=options.craftsmanOnly
        ? `<div class="post-action-group">
             <span class="like-count" aria-label="いいね数">♥ ${post.likes}</span>
             <button class="btn btn-outline btn-consider ${considered?"active":""}" data-consider="${post.id}">
               ${considered?"✓ 検討中":"検討中"}
             </button>
           </div>`
        : `<button class="btn btn-outline btn-like ${liked?"active":""}" data-like="${post.id}">
             ♥ ${liked?"いいね済み":"いいね"} ${post.likes}
           </button>`;

      const title=post.title || "無題の投稿";
      const summary=(post.body||"").replace(/\s+/g," ").trim();
      const shortSummary=summary.length>70 ? summary.slice(0,70)+"…" : summary;

      return `
        <article class="card post-card post-list-card">
          <div class="post-top">
            <div>
              <div class="post-author"><span class="role-badge ${post.role==="craftsman"?"craftsman":"consumer"}"><span class="role-dot"></span>${post.role==="craftsman"?"職人":"ユーザー"}</span><span class="post-user">${escapeHtml(post.user)}</span></div>
              <div class="post-date">${escapeHtml(post.createdAt)}</div>
            </div>
          </div>

          <a class="post-title-link" href="${(getSession()?.role === "craftsman") ? "post-detail.html" : "customer-post-detail.html"}?id=${encodeURIComponent(post.id)}">
            ${escapeHtml(title)}
          </a>
          <p class="post-summary">${escapeHtml(shortSummary)}</p>

          <div class="post-tags">
            ${post.tags.map(t=>`<span class="tag">#${escapeHtml(t)}</span>`).join("")}
          </div>

          <div class="post-actions">
            ${options.craftsmanOnly ? `<div class="like-count">♥ ${post.likes} いいね</div>` : `<div></div>`}
            <div>${action}</div>
          </div>
        </article>`;
    }).join("");

    $$('[data-like]',el).forEach(btn=>{
      btn.addEventListener('click',e=>{
        e.preventDefault();
        e.stopPropagation();
        toggleLike(Number(btn.dataset.like),options);
      });
    });
    $$('[data-consider]',el).forEach(btn=>{
      btn.addEventListener('click',e=>{
        e.preventDefault();
        e.stopPropagation();
        toggleConsider(Number(btn.dataset.consider),options);
      });
    });
  }

  function toggleLike(id,options={}){
    const s=getSession();
    if(!s){
      location.href="customer-login.html";
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
    if(options.detail){
      renderPostDetail("#postDetail", options.detailMode || (s.role === "craftsman" ? "craftsman" : "consumer"));
    }else{
      renderTimeline("#postList",options);
    }
  }

  function toggleConsider(id,options={}){
    const s=getSession();
    if(!s){
      location.href="artisan-login.html";
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
    if(options.detail){
      renderPostDetail("#postDetail", options.detailMode || "craftsman");
    }else{
      renderTimeline("#postList",options);
    }
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
    if(!options.noTagFilter){
      $("#filterButton")?.addEventListener("click",()=>{
      const selectedTag = $("#tagFilter")?.value || "";
      renderTimeline("#postList", {...options, tag:selectedTag});
    });
    $("#clearFilterButton")?.addEventListener("click",()=>{
      if($("#tagFilter")) $("#tagFilter").value = "";
      renderTimeline("#postList", {...options, tag:""});
    });
    }
  }

  function savePostDraft(form){
    const data=Object.fromEntries(new FormData(form).entries());
    const s=getSession();
    // 投稿者名はログイン中アカウントの登録名を自動反映します。
    data.name=s?.displayName || (s?.role==="craftsman" ? "職人ユーザー" : "ユーザー");
    data.tags=data.tags ? [data.tags] : [];
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
      title:d.title || "無題の投稿",
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

  function getPostById(id){
    return getPosts().find(p=>String(p.id)===String(id)) || null;
  }

  function renderPostDetail(target="#postDetail", mode=null){
    const el=$(target);
    if(!el) return;

    const id=new URLSearchParams(location.search).get("id");
    const post=getPostById(id);
    if(!post){
      el.innerHTML=`<div class="card empty">投稿が見つかりません。</div>`;
      return;
    }

    const s=getSession();
    const detailMode=mode || (s?.role === "craftsman" ? "craftsman" : "consumer");
    const liked=!!s && (post.likedBy||[]).includes(s.email);
    const considered=!!s && (post.consideredBy||[]).includes(s.email);

    let action="";
    if(detailMode === "craftsman"){
      action=`<div class="post-action-group">\n        <span class="like-count like-count-large">♥ ${post.likes} いいね</span>\n        <button class="btn btn-outline btn-consider ${considered?"active":""}" id="detailConsider">${considered?"✓ 検討中":"検討中"}</button>\n      </div>`;
    }else{
      action=`<button class="btn btn-outline btn-like ${liked?"active":""}" id="detailLike">♥ ${liked?"いいね済み":"いいね"} ${post.likes}</button>`;
    }

    el.innerHTML=`
      <article class="card detail-card">
        <div class="post-top">
          <div>
            <div class="post-author"><span class="role-badge ${post.role==="craftsman"?"craftsman":"consumer"}"><span class="role-dot"></span>${post.role==="craftsman"?"職人":"ユーザー"}</span><span class="post-user">${escapeHtml(post.user)}</span></div>
            <div class="post-date">${escapeHtml(post.createdAt)}</div>
          </div>
        </div>
        <h1 class="detail-title">${escapeHtml(post.title || "無題の投稿")}</h1>
        <div class="detail-body">${escapeHtml(post.body)}</div>
        <div class="post-tags">${post.tags.map(t=>`<span class="tag">#${escapeHtml(t)}</span>`).join("")}</div>
        <div class="post-actions">
          ${detailMode === "craftsman" ? `<div class="like-count">♥ ${post.likes} いいね</div>` : `<div></div>`}
          <div>${action}</div>
        </div>
      </article>`;

    $("#detailLike",el)?.addEventListener("click",()=>toggleLike(post.id,{detail:true,detailMode:"consumer"}));
    $("#detailConsider",el)?.addEventListener("click",()=>toggleConsider(post.id,{detail:true,detailMode:"craftsman"}));
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
    el.innerHTML=posts.map(p=>{
      const summary=(p.body||"").replace(/\s+/g," ").trim();
      const shortSummary=summary.length>70 ? summary.slice(0,70)+"…" : summary;
      return `
      <article class="card post-card post-list-card">
        <div class="post-top">
          <strong>${escapeHtml(p.user)}</strong>
          <span class="post-date">${escapeHtml(p.createdAt)}</span>
        </div>
        <a class="post-title-link" href="${getSession()?.role === "craftsman" ? "post-detail.html" : "customer-post-detail.html"}?id=${encodeURIComponent(p.id)}">${escapeHtml(p.title || "無題の投稿")}</a>
        <p class="post-summary">${escapeHtml(shortSummary)}</p>
        <div class="post-tags">${p.tags.map(t=>`<span class="tag">#${escapeHtml(t)}</span>`).join("")}</div>
        <div class="post-actions"><span style="font-size:12px;color:var(--muted)">♥ ${p.likes} いいね</span></div>
      </article>`;
    }).join("");
  }

  return {
    $, $$, escapeHtml, seed, getSession, setSession, logout, role, roleLabel, getCraftsmanProfile,
    homeByRole, requireLogin, login, getPosts, savePosts, getNotifications,
    addNotification, renderHeader, renderTimeline, toggleLike, toggleConsider,
    populateTags, setupTimeline, savePostDraft, getPostDraft, createPost,
    saveAccountDraft, getAccountDraft, registerAccount, updateAccount, getMyPosts, renderMyPosts, getPostById, renderPostDetail
  };
})();

document.addEventListener("DOMContentLoaded",()=>{
  Traemon.seed();
  Traemon.renderHeader();
});
