#!/usr/bin/env python3

import mimetypes

from flask import Flask

from Routes import app_blueprint


def create_app() -> Flask:
    app = Flask(__name__)
    app.secret_key = "kd'hOvV:n6%0;"
    mimetypes.add_type("application/javascript", ".js")

    # db config
    app.register_blueprint(app_blueprint)

    return app


if __name__ == "__main__":
    app = create_app()
    app.run(host="0.0.0.0", port=8081, debug=True)
