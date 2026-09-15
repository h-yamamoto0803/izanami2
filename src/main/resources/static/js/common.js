// Traemon static mock: notification panel open/close only.

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
  // タグ選択
  // =========================
  const dropdownButton =
      document.getElementById("tagDropdownButton");

  const dropdown =
      document.getElementById("tagDropdown");

  const selectedTags =
      document.getElementById("selectedTags");

  const checkboxes =
      document.querySelectorAll(".tag-checkbox");


  // タグ選択UIが存在しない画面では何もしない
  if (!dropdownButton || !dropdown || !selectedTags) {
    return;
  }


  // プルダウン開閉
  dropdownButton.addEventListener("click", (event) => {
    event.stopPropagation();
    dropdown.classList.toggle("open");
  });


  // 選択済みタグ表示
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


  // 初期表示・戻る・バリデーションエラー時の復元
  updateSelectedTags();


  // 外側をクリックしたら閉じる
  document.addEventListener("click", (event) => {
    if (!event.target.closest(".tag-select")) {
      dropdown.classList.remove("open");
    }
  });

});