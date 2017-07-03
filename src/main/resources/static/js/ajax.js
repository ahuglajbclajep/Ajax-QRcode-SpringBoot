var hash = location.hash.replace(/^#/, "");
if (hash !== "") {
  ajax(hash);
  $("#input_text").val(hash);
}

$("#submit_button").click(submit);
$("#input_text").keypress(function (e) {
  if (e.which == 13) $("#submit_button").click();
});

function submit() {
  var text = $("#input_text").val();
  ajax(text);
  location.hash = text;
}

function ajax(text) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "create");
  xhr.setRequestHeader("Content-Type", "text/plain");
  xhr.responseType = "blob";
  xhr.timeout = 2000;
  xhr.onloadend = function () {
    switch (this.status) {
      case 200:
        done(this.response);
        break;
      default:
        fail();
    }
  };
  xhr.ontimeout = function (e) {
    fail();
  };
  xhr.send(text);
}

function done(response) {
  $("#form").removeClass("has-error");
  $("#qrcode_image").attr({
    src: URL.createObjectURL(response),
    alt: hash
  });
}

function fail() {
  $("#form").addClass("has-error");
  alert("Can't resolve request.");
}
