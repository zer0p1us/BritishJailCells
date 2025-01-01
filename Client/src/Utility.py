def clean_tristate_checkbox_values(param: str) -> str:
    """Needed to clean tristate param values as they may differ"""
    match param:
        case "":
            return None
        case "none":
            return None
        case _:
            return param
