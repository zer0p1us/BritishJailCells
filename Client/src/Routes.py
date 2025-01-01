from flask import Blueprint, redirect, render_template, request, session

from Api import Api
from models.Rooms import Rooms
import Utility

# holds all the routes to be registered in the app file
app_blueprint = Blueprint("app", __name__)
api = Api()


@app_blueprint.route("/")
def main():
    session["room_data"] = None
    session["searchTerms"] = None
    session["furnished"] = None
    session["liveInLandlord"] = None
    session["billsIncluded"] = None
    return redirect("/search")


@app_blueprint.route("/home")
def home():
    headings = [
        "#",
        "Names",
        "Rent",
        "Location",
        "Furnished",
        "Live in Landlord",
        "Bills included",
        "Shared bathroom",
        "Shared with",
        "Amenities",
    ]
    room_data = Rooms.model_validate_json(session["room_data"])
    data = [
        (
            room.id,
            room.name,
            "£" + room.price_per_month_gbp.__str__(),
            room.location.__str__(),
            room.details.furnished,
            room.details.live_in_landlord,
            room.details.bills_included,
            room.details.bathroom_shared,
            room.details.shared_with,
            room.details.get_amenities(),
        )
        for room in room_data.rooms
    ]
    return render_template("home.html", headings=headings, data=data)


@app_blueprint.route("/search", methods=["POST", "GET"])
def search():
    search_terms = request.form.get("searchTerms")
    furnished = Utility.clean_tristate_checkbox_values(request.form.get("furnished"))
    live_in_landlord = Utility.clean_tristate_checkbox_values(
        request.form.get("liveInLandlord")
    )
    bills_included = Utility.clean_tristate_checkbox_values(
        request.form.get("billsIncluded")
    )
    bathroom_shared = Utility.clean_tristate_checkbox_values(
        request.form.get("bathroomShared")
    )
    max_monthly_rent = request.form.get("maxMonthlyRent")
    max_shared_with = request.form.get("maxSharedWith")

    room_data = api.search(
        search_terms=search_terms,
        furnished=furnished,
        live_in_landlord=live_in_landlord,
        bills_included=bills_included,
        bathroom_shared=bathroom_shared,
        max_shared_with=max_shared_with,
        max_monthly_rent=max_monthly_rent,
    )

    session["room_data"] = room_data.json()
    session["searchTerms"] = search_terms
    session["furnished"] = furnished
    session["liveInLandlord"] = live_in_landlord
    session["billsIncluded"] = bills_included
    session["bathroomShared"] = bathroom_shared
    session["maxMonthlyRent"] = max_monthly_rent
    session["maxSharedWith"] = max_shared_with
    return redirect("/home")
