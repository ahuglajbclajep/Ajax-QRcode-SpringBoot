onload = submitFromHash;
onhashchange = submitFromHash;

$("#submit_button").click(submitFromForm);
$("#input_text").keypress(function (e) {
  if (e.which == 13) $("#submit_button").click();
});

function submitFromHash() {
  var hash = location.hash.replace(/^#/,"");
  $("#input_text").val(hash);
  if (hash !== "") ajax(hash);
}

function submitFromForm() {
  var text = $("#input_text").val();
  location.hash = text;
  if (text !== "") ajax(text);
}

function ajax(contents) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "create");
  xhr.setRequestHeader("Content-Type", "text/plain");
  xhr.responseType = "blob";
  xhr.timeout = 2000;
  xhr.onloadend = function () {
    switch (this.status) {
      case 200:
        done(this.response, contents);
        break;
      default:
        fail();
    }
  };
  xhr.ontimeout = function (e) {
    alert("Connection timed out.");
  };
  xhr.send(contents);
}

function done(response, contents) {
  $("#form").removeClass("has-error");
  $("#qrcode_image").attr({
    src: URL.createObjectURL(response),
    alt: contents
  });
}

function fail() {
  $("#form").addClass("has-error");
  alert("Can't resolve request.");
}
