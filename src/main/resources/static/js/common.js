document.addEventListener("DOMContentLoaded", () => {

  // =========================
  // 通知パネル
  // =========================
  const button = document.querySelector("#notificationButton");
  const panel = document.querySelector("#notificationPanel");

  if (button && panel) {
    button.addEventListener("click", (event) => {
      event.stopPropagation();
      panel.classList.toggle("open");
    });

    document.addEventListener("click", (event) => {
      if (!event.target.closest(".notification-wrap")) {
        panel.classList.remove("open");
      }
    });
  }


  // =========================
  // 投稿作成：自由入力タグ
  // =========================
  const tagInput = document.getElementById("tagInput");
  const tagList = document.getElementById("tagList");
  const tagHiddenInputs = document.getElementById("tagHiddenInputs");

  if (tagInput && tagList && tagHiddenInputs) {

    const tags = Array.from(
      tagHiddenInputs.querySelectorAll('input[name="tags"]')
    ).map(input => input.value);

    function renderTags() {

      tagList.innerHTML = "";
      tagHiddenInputs.innerHTML = "";

      tags.forEach((tagName, index) => {

        // 表示用
        const tag = document.createElement("span");
        tag.classList.add("selected-tag");
        tag.textContent = "#" + tagName;

        const removeButton = document.createElement("button");
        removeButton.type = "button";
        removeButton.textContent = "×";

        removeButton.addEventListener("click", () => {
          tags.splice(index, 1);
          renderTags();
        });

        tag.appendChild(removeButton);
        tagList.appendChild(tag);

        // POST用
        const hidden = document.createElement("input");
        hidden.type = "hidden";
        hidden.name = "tags";
        hidden.value = tagName;

        tagHiddenInputs.appendChild(hidden);
      });
    }

    function addTag() {

      const tagName = tagInput.value.trim();

      if (tagName === "") {
        return;
      }

      if (!tags.includes(tagName)) {
        tags.push(tagName);
      }

      tagInput.value = "";
      renderTags();
    }

    // Enterで追加
    tagInput.addEventListener("keydown", (event) => {

      if (event.key === "Enter") {
        event.preventDefault();
        addTag();
      }
    });

    // 確認ボタンを押した時も、入力途中のタグを追加
    const form = tagInput.closest("form");

    if (form) {
      form.addEventListener("submit", () => {
        addTag();
      });
    }

    renderTags();
  }
  // =========================
  // 既存タグ選択
  // =========================
  const dropdownButton =
      document.getElementById("tagDropdownButton");

  const dropdown =
      document.getElementById("tagDropdown");

  const selectedTags =
      document.getElementById("selectedTags");

  const checkboxes =
      document.querySelectorAll(".tag-checkbox");


  if (!dropdownButton || !dropdown || !selectedTags) {
    return;
  }


  dropdownButton.addEventListener("click", (event) => {
    event.stopPropagation();
    dropdown.classList.toggle("open");
  });


  function updateSelectedTags() {

    selectedTags.innerHTML = "";

    checkboxes.forEach(checkbox => {

      if (!checkbox.checked) {
        return;
      }

      const tag = document.createElement("span");
      tag.classList.add("selected-tag");

      tag.append(
        document.createTextNode(checkbox.dataset.name)
      );

      const removeButton =
          document.createElement("button");

      removeButton.type = "button";
      removeButton.textContent = "×";

      removeButton.addEventListener("click", () => {
        checkbox.checked = false;
        updateSelectedTags();
      });

      tag.appendChild(removeButton);
      selectedTags.appendChild(tag);
    });
  }


  checkboxes.forEach(checkbox => {
    checkbox.addEventListener(
      "change",
      updateSelectedTags
    );
  });


  updateSelectedTags();


  document.addEventListener("click", (event) => {
    if (!event.target.closest(".tag-select")) {
      dropdown.classList.remove("open");
    }
  });

});