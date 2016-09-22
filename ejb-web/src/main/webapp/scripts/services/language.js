angular.module('bcServices')
	.service("language", [function() {
		var lang = {};
		lang.albania = "Albania";
		lang.armenia = "Armenia";
		lang.azerbeijan = "Azerbejdżan";
		lang.belarus = "Białoruś";
		lang.bosniaandherzegovine = "Bośnia i Hercegowina";
		lang.bulgaria = "Bułgaria";
		lang.croatia = "Chorwacja";
		lang.czechrepublic = "Czechy";
		lang.estonia = "Estonia";
		lang.georgia = "Gruzja";
		lang.greece = "Grecja";
		lang.hungary = "Węgry";
		lang.kazakhstan = "Kazachstan";
		lang.kosovo = "Kosowo";
		lang.kyrgyzstan = "Kirgistan";
		lang.latvia = "Łotwa";
		lang.lithuania = "Litwa";
		lang.macedonia = "Macedonia";
		lang.moldova = "Mołdawia";
		lang.montenegro = "Czarnogóra";
		lang.poland = "Polska";
		lang.romania = "Rumunia";
		lang.russia = "Rosja";
		lang.serbia = "Serbia";
		lang.slovakia = "Słowacja";
		lang.slovenia = "Słowenia";
		lang.turkey = "Turcja";
		lang.turkmenistan = "Turkemenistan";
		lang.Ukraine = "Ukraina";
		lang.uzbekistan = "Uzbekistan";

		return {
			countryToPL: function(countryName) {
				return lang[countryName.toLowerCase().trim()];
			}
		};
	}])