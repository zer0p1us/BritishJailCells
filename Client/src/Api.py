import configparser
import os

import requests

from models.Rooms import Rooms
from models.History import History


class Api:
    _instance = None

    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            cls._instance = super(Api, cls).__new__(cls, *args, **kwargs)
        return cls._instance

    def __init__(self):
        self.BASE_API = "BritishJailCells/api/"

        config_file_path = os.path.join(
            os.path.dirname(__file__), "..", "config.properties"
        )
        config = configparser.ConfigParser()
        config.read(config_file_path)

        self.BASE_API = (
            "http://"
            + config["orchestrator"]["ip"]
            + ":"
            + config["orchestrator"]["port"]
            + "/"
            + self.BASE_API
        )
        self.SEARCH_API = self.BASE_API + "search/"
        self.APPLY_API = self.BASE_API + "apply/"
        self.CANCEL_API = self.BASE_API + "cancel/"
        self.HISTORY_API = self.BASE_API + "history/"

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
        params = {"roomId": str(room_id), "userId": user_id}
        requests.post(self.APPLY_API, params=params)

    def cancel(self, application_ref, user_id) -> None:
        params = {"applicationRef": application_ref, "userId": user_id}
        requests.put(self.CANCEL_API, params=params)
