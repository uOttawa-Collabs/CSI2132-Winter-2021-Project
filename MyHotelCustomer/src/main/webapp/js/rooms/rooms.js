function bookingSuccess(alertBox)
{
    showSuccessAlert(alertBox, "Booking success");
}

function addColumn(json, index, tbody)
{
    let s = ""
    s += "<tr>";

    s += "<td>";
    s += json["id"];
    s += "</td>";

    s += "<td>";
    s += json["price"];
    s += "</td>";

    s += "<td>";
    s += json["type"];
    s += "</td>";

    s += "<td>";
    s += json["capacity"];
    s += "</td>";

    s += "<td>";
    s += json["amenity"].toString().replaceAll(',', '<br>');
    s += "</td>";

    s += "<td>";
    s += json["extensibility"].toString().replaceAll(',', '<br>');
    s += "</td>";

    s += "<td>";
    s += json["view"].toString().replaceAll(',', '<br>');
    s += "</td>";

    s += "<td>";
    s += '<button id="button-' + index + '" class="btn btn-primary">Book</button>'
    s += "</td>";

    s += "</tr>";
    tbody.append(s);
}

function generateButtonCallback(brandName, hotelName, id, alertBox)
{
    const apiAddress = "BookServlet";
    return () =>
    {
        $.ajax(
            {
                async      : true,
                contentType: "application/json; charset=utf-8",
                method     : "POST",
                url        : apiAddress,
                timeout    : 3000,
                dataType   : "json",
                data       : JSON.stringify(
                    {
                        "brandName": brandName,
                        "hotelName": hotelName,
                        "id"       : id
                    }),
                success(data, textStatus)
                {
                    switch (data["code"])
                    {
                        case "0":
                            bookingSuccess(alertBox);
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
    };
}

function parseRoomListJson(hotelBrand, hotelName, array, tbody, alertBox)
{
    "use strict";
    try
    {
        array.forEach(
            (value, index) =>
            {
                addColumn(value, index, tbody);
            });
        for (let i = 0; i < array.length; ++i)
        {
            let button = $("button#button-" + i);
            if (button !== undefined)
            {
                button.click(generateButtonCallback(hotelBrand, hotelName, array[i]["id"]), alertBox);
            }
        }
    }
    catch (e)
    {
        showDangerAlert(alertBox, "Error when processing data: " + e);
    }
}

function notLoggedInHandler(alertBox)
{
    "use strict";
    showDangerAlert(alertBox, "You are not logged in. Navigating to login page...");
    setTimeout(
        () =>
        {
            window.location.replace("index.html");
        },
        2000
    );
}

function hotelNotFoundHandler(alertBox)
{
    "use strict";
    showDangerAlert(alertBox, "The specified hotel is not found. Navigating to hotel selection page...");
    setTimeout(
        () =>
        {
            window.location.replace("hotels.html");
        },
        2000
    );
}

function getRoomJsonList(brandName, hotelName, alertBox, callback)
{
    "use strict";
    const apiAddress = "QueryServlet"
    $.ajax(
        {
            async      : true,
            contentType: "application/json; charset=utf-8",
            method     : "POST",
            url        : apiAddress,
            timeout    : 3000,
            dataType   : "json",
            data       : JSON.stringify(
                {
                    "query"  : "room",
                    "content":
                        {
                            "brandName": brandName,
                            "hotelName": hotelName
                        }
                }),
            success(data, textStatus)
            {
                switch (data["code"])
                {
                    case "0":
                        callback(data["data"]);
                        break;
                    case "403":
                        notLoggedInHandler(alertBox);
                        break;
                    case "404":
                        hotelNotFoundHandler(alertBox);
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
    )
}

// Event binding
$(document).ready(
    () =>
    {
        "use strict";
        const alertBox = $("div#alert");
        const tbody = $("table#table tbody");

        let brandName = getQueryVariable("brandName");
        let hotelName = getQueryVariable("hotelName");

        if (brandName === null || hotelName === null)
        {
            window.location.replace("hotels.html");
        }

        getRoomJsonList(
            brandName,
            hotelName,
            alertBox,
            (array) =>
            {
                parseRoomListJson(brandName, hotelName, array, tbody, alertBox);
            }
        )
    }
)
