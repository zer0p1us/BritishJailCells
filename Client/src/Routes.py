from flask import Blueprint, redirect, render_template, request, session

from Api import Api
from models.Rooms import Rooms

# holds all the routes to be registered in the app file
app_blueprint = Blueprint("app", __name__)
room_data = None
api = Api()


@app_blueprint.route("/")
def main():
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


@app_blueprint.route("/search")
def search():
    search_terms = request.form.get("searchTerms")
    room_data = api.search(search_terms=search_terms)
    session["room_data"] = room_data.json()
    return redirect("/home")
