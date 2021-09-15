from typing import Any, Text, Dict, List

from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher

from weather import get_weather


class ActionGetWeather(Action):

    def name(self) -> Text:
        return "action_get_weather"

    def run(self,
            dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        city = tracker.slots.get("name")
        temp = int(get_weather(city)['temp'] - 273)
        dispatcher.utter_template("utter_city_weather", tracker, city=city, temp=temp)

        return []


class CallSomeone(Action):

    def name(self) -> Text:
        return "action_call_someone"

    def run(self,
            dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        name = tracker.slots.get("name")
        dispatcher.utter_template("utter_city_weather", tracker, name=name)

        return []
