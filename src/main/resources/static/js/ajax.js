var hash = location.hash.replace(/^#/, "");
if (hash !== "") {
  ajax(hash);
  $("#input_text").val(hash);
}

$("#submit_button").click(submit);
$("#input_text").keypress(function (event) {
  if (event.which == 13) $("#submit_button").click();
});

function submit() {
  var text = $("#input_text").val();
  ajax(text);
  location.hash = text;
}

function ajax(text) {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
      switch (this.status) {
        case 200:
          $("#form").removeClass("has-error has-feedback");
          $("#qrcode_image").attr("src", URL.createObjectURL(this.response));
          break;
        default:
          $("#form").addClass("has-error has-feedback");
          alert("Can't resolve request.");
      }
    }
  };

  xhr.open("POST", "create");
  xhr.setRequestHeader("Content-Type", "text/plain");
  xhr.responseType = "blob";
  xhr.send(text);
}
