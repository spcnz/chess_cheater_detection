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
            "subject": f"{namespace}.GamePlayer", "version": 1}],
    "GameStatistic": [],
    "Move": [],
    "MoveWithScore": [],
    "PuzzleStatistic": [],
    "Game": [ {"name": f"{namespace}.GameClock", "subject": f"{namespace}.GameClock", "version": 1},
              {"name": f"{namespace}.GamePlayers", "subject": f"{namespace}.GamePlayers", "version": 1},
              {"name": f"{namespace}.GameClock", "subject": f"{namespace}.GameClock", "version": 1},
            ],
    "Player": [ {"name": f"{namespace}.PuzzleStatistic", "subject": f"{namespace}.PuzzleStatistic", "version": 1},
              {"name": f"{namespace}.GameStatistic", "subject": f"{namespace}.GameStatistic", "version": 1}
              ],
    "GameResultStatus": [],
    "GameResultVariant": [],
    "GameResult": [ {"name": f"{namespace}.GameResultStatus", "subject": f"{namespace}.GameResultStatus", "version": 1},
                    {"name": f"{namespace}.GameResultVariant", "subject": f"{namespace}.GameResultVariant", "version": 1},
                  ],
    "GameWithMove": [ {"name": f"{namespace}.GameClock", "subject": f"{namespace}.GameClock", "version": 1},
              {"name": f"{namespace}.GamePlayers", "subject": f"{namespace}.GamePlayers", "version": 1},
              {"name": f"{namespace}.GameClock", "subject": f"{namespace}.GameClock", "version": 1},
            ],
 
}

def read_schema(path: str) -> str:
    with open(path, "r") as fp:
        return fp.read()



if __name__ == "__main__":
    schema_registry_url = sys.argv[1]
    for subject_name in schema_references:
        schema = read_schema(path=f"/init/avro/{subject_name}.avsc")
        subject = f"{namespace}.{subject_name}"
        body = {"schema": schema, "schemaType": "AVRO"}
        if schema_references[subject_name]:
            body["references"] =  schema_references[subject_name]

        headers = {"Content-Type": "application/json"}
        url = f"{schema_registry_url}/subjects/{subject}/versions"
        response = requests.post(url, data=json.dumps(body), headers=headers)
        response.raise_for_status()

        print(f"{subject=} registered successfully")
