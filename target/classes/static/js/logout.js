document.addEventListener('DOMContentLoaded', function() {
  var logoutForm = document.getElementById('logout-form');
  if (logoutForm) {
    logoutForm.addEventListener('submit', function() {
      localStorage.removeItem('chatMessages'); // Clear chat messages from local storage
    });
  }
});