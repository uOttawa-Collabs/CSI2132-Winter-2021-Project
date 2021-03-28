function addColumn(json, index, tbody)
{
    let s = ""
    s += "<tr>";

    s += "<td>";
    s += json["brandName"];
    s += "</td>";

    s += "<td>";
    s += json["hotelName"];
    s += "</td>";

    s += "<td>";
    s += json["starCategory"];
    s += "</td>";

    s += "<td>";
    s += json["address"];
    s += "</td>";

    s += "<td>";
    s += json["phoneNumber"];
    s += "</td>";

    s += "<td>";
    s += json["roomsAvailable"];
    s += "</td>";

    s += "<td>";
    if (parseInt(json["roomsAvailable"]) > 0)
        s += '<button id="button-' + index + '" class="btn btn-primary">Book</button>'
    s += "</td>";

    s += "</tr>";
    tbody.append(s);
}

function generateButtonCallback(brandName, hotelName)
{
    return () =>
    {
        window.location.href = "rooms.html?brandName=" + encodeURIComponent(
            brandName) + "&hotelName=" + encodeURIComponent(hotelName);
        encodeURIComponent(hotelName);
    };
}

function parseHotelListJson(array, tbody, alertBox)
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
                button.click(generateButtonCallback(array[i]["brandName"], array[i]["hotelName"]));
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

function getHotelJsonList(alertBox, callback)
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
                    "query": "hotel",
                    "content": null
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

        getHotelJsonList(
            alertBox,
            (array) =>
            {
                parseHotelListJson(array, tbody, alertBox);
            }
        )
    }
)
