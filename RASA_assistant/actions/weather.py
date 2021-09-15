import requests


def get_weather(city):
    api_address = 'http://api.openweathermap.org/data/2.5/weather?appid=0c42f7f6b53b244c78a418f4f181282a&q='
    url = api_address + city
    json_data = requests.get(url).json()
    format_add = json_data['main']
    print(format_add)
    print("Weather is {0} Temperature is mininum {1} Celcius and maximum is {2} Celcius".format(
        json_data['weather'][0]['main'], int(format_add['temp_min'] - 273), int(format_add['temp_max'] - 272)))
    return format_add
