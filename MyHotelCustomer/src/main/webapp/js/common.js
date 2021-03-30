function showDangerAlert(alertBox, message)
{
    "use strict";
    alertBox.text(message);
    alertBox.removeClass();
    alertBox.addClass("alert alert-danger");
    alertBox.css("display", "");
}

function showSuccessAlert(alertBox, message)
{
    "use strict";
    alertBox.text(message);
    alertBox.removeClass();
    alertBox.addClass("alert alert-success");
    alertBox.css("display", "");
}

function hideAlert(alertBox)
{
    "use strict";
    alertBox.css("display", "none");
}

function setInputUnverified(inputBox)
{
    "use strict";
    inputBox.addClass("is-invalid");
}

function ajaxErrorHandler(jqXHR, textStatus, errorThrown, serverMessage, alertBox)
{
    "use strict";
    if (jqXHR !== null)
    {
        console.log("Server response:");
        console.log(jqXHR.responseText);
    }

    showDangerAlert(alertBox, "Error on request: " + serverMessage + " (" + textStatus + ", " + errorThrown + ")");
}

function getQueryVariable(variable)
{
    "use strict";
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++)
    {
        let pair = vars[i].split("=");
        if (pair[0] === variable)
        {
            return decodeURIComponent(pair[1]);
        }
    }
    return null;
}
