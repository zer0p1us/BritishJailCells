import requests

from models.Rooms import Rooms
from models.History import History


class Api:
    BASE_API = "http://localhost:8080/BritishJailCells/api/"
    SEARCH_API = BASE_API + "search/"
    APPLY_API = BASE_API + "apply/"
    CANCEL_API = BASE_API + "cancel/"
    HISTORY_API = BASE_API + "history/"

    def search(
        self,
        search_terms=None,
        max_monthly_rent=None,
        furnished=None,
        live_in_landlord=None,
        max_shared_with=None,
        bills_included=None,
        bathroom_shared=None,
    ) -> Rooms:
        params = {}
        if search_terms is not None:
            params["searchTerms"] = search_terms
        if max_monthly_rent is not None:
            params["maxMonthlyRent"] = max_monthly_rent
        if furnished is not None:
            params["furnished"] = furnished
        if live_in_landlord is not None:
            params["liveInLandlord"] = live_in_landlord
        if max_shared_with is not None:
            params["maxSharedWith"] = max_shared_with
        if bills_included is not None:
            params["billsIncluded"] = bills_included
        if bathroom_shared is not None:
            params["bathroomShared"] = bathroom_shared
        return Rooms.model_validate_json(
            requests.get(self.SEARCH_API, params=params).text
        )

    def history(self, room_id) -> History:
        params = {"roomId": room_id}

        return History.model_validate_json(
            requests.get(self.HISTORY_API, params=params).text
        )

    def apply(self, room_id, user_id) -> None:
        print("apply not implemented")

    def cancel(self, application_ref, user_id) -> None:
        print("cancel not implemented")
