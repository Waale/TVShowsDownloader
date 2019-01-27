const notificationTemplate = '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
    '<span data-notify="icon"></span> ' +
    '<span data-notify="title"><b>{1}</b></span><br/> ' +
    '<span data-notify="message">{2}</span>' +
    '</div>';

function notify(title, message, type) {
    $.notify({
        title: title,
        message: message
    },{
        type: type,
        mouse_over: "pause",
        template: notificationTemplate
    });
}

function notifySuccess(title, message) {
    notify(title, message, "success");
}

function notifyError(title, message) {
    notify(title, message, "danger");
}