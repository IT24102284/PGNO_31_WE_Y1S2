// Focus the username field on page load
document.addEventListener('DOMContentLoaded', () => {
  const input = document.querySelector('#username');
  if (input) input.focus();
});