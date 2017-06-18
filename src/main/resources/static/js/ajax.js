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
    $.ajax({
        url: "create",
        type: "POST",
        contentType: "text/plain",
        data: text,
        xhr: function () {
            var xhr = new XMLHttpRequest();
            xhr.responseType = "blob";
            return xhr;
        }
    })
    .done(function (response) {
        $("#qrcode_image").attr("src", URL.createObjectURL(response));
    })
    .fail(function () {
        alert("Can't resolve request.");
    });
}
