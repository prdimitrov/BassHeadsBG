document.addEventListener("DOMContentLoaded", function () {
  var continueButton = document.getElementById("continue-button");
  var overlay = document.getElementById("overlay");
  var audio = document.getElementById("background-audio");

  continueButton.addEventListener("click", function () {
    audio.play();
    overlay.style.display = "none";
  });
});

/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function (event) {
  if (!event.target.matches('.dropbtn') && !event.target.closest('.dropdown-content')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
       openDropdown.classList.remove('show');
      }
    }
  }
}

// Prevent dropdown from closing when clicking inside the form
document.getElementById("myDropdown").addEventListener("click", function(event){
  event.stopPropagation();
});