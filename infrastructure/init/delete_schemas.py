import json
import requests
import sys

namespace = "yauza.avro.message.chess"
schema_references = {
    "GameClock": [],
    "GameUser": [],
    "GamePlayer": [
        {
            "name": f"{namespace}.GameUser",
            "subject": f"{namespace}.GameUser",
            "version": 1,
        }
    ],
    "GamePlayers": [
        {
            "name": f"{namespace}.GamePlayer",
            "subject": f"{namespace}.GamePlayer",
            "version": 1,
        }
    ],
    "GameStatistic": [],
    "Move": [],
    "MoveWithScore": [],
    "PlayerMove": [],
    "PuzzleStatistic": [],
    "Game": [
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
        {
            "name": f"{namespace}.GamePlayers",
            "subject": f"{namespace}.GamePlayers",
            "version": 1,
        },
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
    ],
    "Player": [
        {
            "name": f"{namespace}.PuzzleStatistic",
            "subject": f"{namespace}.PuzzleStatistic",
            "version": 1,
        },
        {
            "name": f"{namespace}.GameStatistic",
            "subject": f"{namespace}.GameStatistic",
            "version": 1,
        },
    ],
    "GameResultStatus": [],
    "GameResultVariant": [],
    "GameResult": [
        {
            "name": f"{namespace}.GameResultStatus",
            "subject": f"{namespace}.GameResultStatus",
            "version": 1,
        },
        {
            "name": f"{namespace}.GameResultVariant",
            "subject": f"{namespace}.GameResultVariant",
            "version": 1,
        },
        {
            "name": f"{namespace}.GamePlayers",
            "subject": f"{namespace}.GamePlayers",
            "version": 1,
        },
    ],
    "GameWithMoveScore": [
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
        {
            "name": f"{namespace}.GamePlayers",
            "subject": f"{namespace}.GamePlayers",
            "version": 1,
        },
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
    ],
    "GameScore": [
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
        {
            "name": f"{namespace}.GamePlayers",
            "subject": f"{namespace}.GamePlayers",
            "version": 1,
        },
        {
            "name": f"{namespace}.GameClock",
            "subject": f"{namespace}.GameClock",
            "version": 1,
        },
    ],
    "PlayerGameKpi": [],
}


def delete_schema(schema_registry_url: str, subject: str, version: int):
    url = f"{schema_registry_url}/subjects/{namespace}.{subject}/versions/{version}"
    response = requests.delete(url)
    if response.status_code not in [200, 404]:
        print(response)

    response = requests.delete(f"{url}/?permanent=true")
    if response.status_code in [200, 404]:
        print(f"{subject} deleted permanently")
    elif response.status_code = 404:
        print(f"Subject {subject} with versin {version} not found.")
    else:
        print(response)


if __name__ == "__main__":
    schema_registry_url = sys.argv[1]
    indepentant_subjects = [
        subject for subject, references in schema_references.items() if references == []
    ]
    for subject in indepentant_subjects:
        versions = requests.get(
            f"{schema_registry_url}/subjects/{namespace}.{subject}/versions"
        )
        if not versions:
            break
        for version in str(versions.content, "utf-8").strip("[]").split(","):
            delete_schema(schema_registry_url, subject, version)

    deepentant_subjects = [
        subject for subject, references in schema_references.items() if references != []
    ]
    for subject in deepentant_subjects:
        versions = requests.get(
            f"{schema_registry_url}/subjects/{namespace}.{subject}/versions"
        )
        if not versions:
            break
        for version in str(versions.content, "utf-8").strip("[]").split(","):
            delete_schema(schema_registry_url, subject, version)
