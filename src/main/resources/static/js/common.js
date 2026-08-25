// Traemon static mock: notification panel open/close only.

document.addEventListener("DOMContentLoaded", () => {
    const button = document.querySelector("#notificationButton");
    const panel = document.querySelector("#notificationPanel");

    if (!button || !panel) return;

    button.addEventListener("click", (event) => {
        event.stopPropagation();
        panel.classList.toggle("open");
    });

    document.addEventListener("click", (event) => {
        if (!event.target.closest(".notification-wrap")) {
            panel.classList.remove("open");
        }
    });
});

const tagInput = document.getElementById("tagInput");
const tagList = document.getElementById("tagList");
const tagHiddenInputs = document.getElementById("tagHiddenInputs");

const tags = [];

tagInput.addEventListener("keydown", function(event) {

    if (event.key === "Enter") {

        // Enterでformがsubmitされるのを防ぐ
        event.preventDefault();

        const tag = tagInput.value.trim();

        if (tag === "") {
            return;
        }

        // 重複防止
        if (tags.includes(tag)) {
            tagInput.value = "";
            return;
        }

        tags.push(tag);

        tagInput.value = "";

        renderTags();
    }
});

function renderTags() {

    tagList.innerHTML = "";
    tagHiddenInputs.innerHTML = "";

    tags.forEach((tag, index) => {

        // 画面表示用
        const tagElement = document.createElement("span");

        tagElement.textContent = tag + " ×";
        tagElement.classList.add("tag-item");

        tagElement.addEventListener("click", function() {
            tags.splice(index, 1);
            renderTags();
        });

        tagList.appendChild(tagElement);

        // Spring送信用
        const hidden = document.createElement("input");

        hidden.type = "hidden";
        hidden.name = "tags";
        hidden.value = tag;

        tagHiddenInputs.appendChild(hidden);
    });
}