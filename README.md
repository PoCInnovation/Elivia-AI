# Elivia-AI


## The future Voice assistant of the [/e/ OS](https://e.foundation/e-os/)

Take control of your smartphone with your voice. Like Google Assistant, Alexa or Cortana, Elivia is a voice assistant but free and open-source.
It can help you to do your everyday task.
It is private by default and completely customizable.

<img src="https://user-images.githubusercontent.com/34518035/127216535-1226ba3d-6891-4159-9d5a-cff1e9d07c28.png" width="25%">


### How it's work ?
With a android app and the help of the [Vosk](https://alphacephei.com/vosk/android) lib to translate your speech to text.
To detect the intention and understande what you want we use RASA as NLU and Spacy as NLP. After this we extract all the entities (like the personne you want to call).
With all of this we take action !

### What Elivia understand

| Intent | Server | App | Descritption|
| :------| :----- | :-- | :-----------|
| hello | :heavy_check_mark: | :heavy_check_mark: | say hello |
| mood | :heavy_check_mark: | :heavy_check_mark: | ask your mood |
| open | :heavy_check_mark: | :heavy_check_mark: | open an application on the phone |
| search | :heavy_check_mark: | :heavy_check_mark: | search somthing on the Web |
| alarm | :heavy_check_mark: | :heavy_check_mark: | set an alarm |
| clock | :heavy_check_mark: | :x: | get the time for anywhere |
| chrono | :heavy_check_mark: | :x: | start a chrono |
| weather | :heavy_check_mark: | :x: | get the weather for anywhere |
| plan | :heavy_check_mark: | :x: | start the naviguation map to go anywhere |
| call | :heavy_check_mark: | :heavy_check_mark: | make a call to one of your contact |
| message | :heavy_check_mark: | :heavy_check_mark: | text a message to one of your contact |
| sport | :heavy_check_mark: | :x: | start a sport session |
| music | :heavy_check_mark: | :heavy_check_mark: | start a music |
| calculator | :heavy_check_mark: | :x: | make calcul |
| reminder | :heavy_check_mark: | :heavy_check_mark: | add a reminder |

## Installation:

### Server Side

	git clone https://github.com/PoCInnovation/Elivia-AI.git
	cd Elivia-AI/server
  source ./venv/bin/activate
  rasa train
	rasa run --enable-api

### Android app

	git clone https://github.com/PoCInnovation/Elivia-AI.git
	cd Elivia-AI/androidApp
	open the project with android studio

### RASA shell
	
	cd Elivia-AI/RASA_assistant/actions
	rasa run actions		(Starts an action server using the Rasa SDK.)
	cd ../
	rasa shell 			(Loads your trained model and lets you talk to your assistant on the command line.)


## Contributors

[@RedGinor](https://github.com/RedGinor) Mark BEKKER
[@celestecote21](https://github.com/celestecote21) C??leste Cote
[@Mikatech](https://github.com/Mikatech) Mikael VALLENET

