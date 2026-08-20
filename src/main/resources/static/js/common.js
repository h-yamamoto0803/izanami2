// Traemon common UI behavior.
// Business logic, session management, data rendering and navigation are handled by Spring/Thymeleaf.

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
