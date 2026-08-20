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
