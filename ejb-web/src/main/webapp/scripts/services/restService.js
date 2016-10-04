angular.module('bcServices')
	.service("restService", ['$http', '$sessionStorage', function($http, $sessionStorage) {
		return {
			adminController: function() {
				return {
					csrfPrevent: function() {
						return $http.get("./admin/login");
					},
					addCap: function(base64, capt, capb, beer, countryId, countryName) {
						return $http({
							method: "POST",
							url: "./rest/admin/collect",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							data: {baseimage: base64},
							params: { captext: capt, capbrand: capb, beer: beer, countryId: countryId, countryName: countryName }
						});
					},
					deleteCap: function(capId) {
						return $http({
							method: "DELETE",
							url: "./rest/admin/collect",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { capId: capId }
						});
					},
					editCap: function(id, country, captext, capbrand, beer) {
						return $http({
							method: "PUT",
							url: "./rest/admin/collect",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { id: id, country: country, captext: captext, capbrand: capbrand, beer: beer }
						});
					},
					addNews: function(title, content) {
						return $http({
							method: "POST",
							url: "./rest/admin/news",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { title: title, content: content }
						});
					},
					editNews: function(id, title, content) {
						return $http({
							method: "PUT",
							url: "./rest/admin/news",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { id: id, title: title, content: content }
						});
					},
					deleteNews: function(id) {
						return $http({
							method: "DELETE",
							url: "./rest/admin/news",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { id: id }
						});
					},
					addTrade: function(base64, filename) {
						return $http({
							method: "POST",
							url: "./rest/admin/trade/upload",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							data: { baseimage: base64 },
							params: { filename: filename }
						});
					},
					deleteTrade: function(ids) {
						return $http({
							method: "DELETE",
							url: "./rest/admin/tradecaps",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { ids: ids }
						});
					}
				};
			},
			countriesController: function() {
				return {
					getCountry: function(countryId) {
						return $http({
							method: "GET",
							url: "./rest/countries/" + countryId
						});
					},
					getCountriesWithAmount: function() {
						return $http({
							method: "GET",
							url: "./rest/countries/amount"
						});
					},
					getCountries: function() {
						return $http({
							method: "GET",
							url: "./rest/countries/all"
						});
					},
					getFlag: function(country) {
						return $http({
							method: "GET",
							url: "./rest/countries/" + country + "/flag"
						});
					}
				};
			},
			authController: function() {
				return {
					logout: function() {
						return $http({
							method: "POST",
							url: "./rest/auth/secure/logout",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken }
							});
					}
				};
			},
			collectController: function() {
				return {
					getCaps: function(country, beer, page, maxPerPage, searchText) {
						return $http({
							method: "GET",
							url: "./rest/collect/caps/" + country + "/" + beer,
							params: { page: page, max : maxPerPage, searchText: searchText }
						});
					},
					getNewestCaps: function() {
						return $http({
							method: "GET",
							url: "./rest/collect/newest/"
						});
					},
					getSingleCap: function(country, capId) {
						return $http({
							method: "GET",
							url: "./rest/collect/cap/" + country + "/" + capId
						});
					},
					getBrand: function(id) {
						return $http({
							method: "GET",
							url: "./rest/collect/brand/" + id
						});
					},
					getCapsAmountProgress: function() {
						return $http({
							method: "GET",
							url: "./rest/collect/progress"
						});
					}
				};
			},
			newsController: function() {
				return {
					getAllNews: function() {
						return $http.get("./rest/news/all");
					},
					getNewsCount: function() {
						return $http.get("./rest/news/count");
					},
					getNewsFromPage: function(pageNo) {
						return $http.get("./rest/news/page/" + pageNo);
					},
					getSingleNews: function(id) {
						return $http.post("./rest/news/" + id);
					}
				};
			},
			tradeController: function() {
				return {
					getMiniCaps: function() {
						return $http({
							method: "GET",
							url: "./rest/trade/minicaps"
						});
					},
					getTradeCap: function(id) {
						return $http({
							method: "GET",
							url: "./rest/trade/tradecap/" + id
						});
					}
				};
			}
		};
	}]);