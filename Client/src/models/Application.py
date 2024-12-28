from pydantic import BaseModel


class Application(BaseModel):
    application_ref: str
    room_id: str
    user_id: str
    status: str
