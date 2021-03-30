function verificationSuccess(alertBox)
{
    "use strict";
    showSuccessAlert(alertBox, "Login success");
    setTimeout(
        () =>
        {
            window.location.href = "hotels.html";
        },
        1000
    );
}

function onSubmit(inputUsernameBox, alertBox)
{
    "use strict";
    const apiAddress = "LoginServlet"
    const username = inputUsernameBox.val();

    if (!username)
        setInputUnverified(inputUsernameBox);
    else
    {
        $.ajax(
            {
                async      : true,
                contentType: "application/json; charset=utf-8",
                method     : "POST",
                url        : apiAddress,
                timeout    : 3000,
                dataType   : "json",
                data       : JSON.stringify({ "username": username.trim() }),
                success(data, textStatus)
                {
                    switch (data["code"])
                    {
                        case "0":
                            verificationSuccess(alertBox);
                            break;
                        default:
                            ajaxErrorHandler(null, textStatus, null, data["message"], alertBox);
                            break;
                    }
                },
                error(jqXHR, textStatus, errorThrown)
                {
                    ajaxErrorHandler(jqXHR, textStatus, errorThrown, null, alertBox);
                }
            }
        );
    }
}

// Event binding
$(document).ready(
    () =>
    {
        "use strict";
        const alertBox = $("div#alert");
        const inputUsernameBox = $("input#username");

        $("button#submit").click(
            () =>
            {
                "use strict";
                hideAlert(alertBox);
                onSubmit(inputUsernameBox, alertBox);
            }
        );
    });
