angular.module('bcServices', [])

	.service("capMover", [function() {

		return {
			capMover : function(caps) {
				var capsLength = caps.length;
				
				var isFirst = function(capIndex) { return capIndex === 0; };
				var isSecond = function(capIndex) { return capIndex === 1; };
				var isNextToLast = function(capIndex) { return capIndex === capsLength - 2; };
				var isLast = function(capIndex) { return capIndex === capsLength - 1; };
				
				var updateFirst = function(fivecaps) {
					fivecaps.push(caps[capsLength - 1]);
					return fivecaps;
				};
				
				var updateFirstTwo = function(fivecaps) {
					fivecaps.push(caps[capsLength - 2]);
					fivecaps.push(caps[capsLength - 1]);
					return fivecaps;
				};
				
				var updateLast = function(fivecaps) {
					fivecaps.push(caps[0]);
					return fivecaps;
				};
				
				var updateLastTwo = function(fivecaps) {
					fivecaps.push(caps[0]);
					fivecaps.push(caps[1]);
					return fivecaps;
				};
				
				var updateOthers = function(fivecaps, capId) {
					for(var i = 0; i < capsLength; i++) {
						if(caps[i].id - 2 <= capId && caps[i].id + 2 >= capId) {
							fivecaps.push(caps[i]);
						}
					}
					return fivecaps;
				};
				
				return {
					isFirst: isFirst,
					isSecond: isSecond,
					isNextToLast: isNextToLast,
					isLast: isLast,
					updateFirst: updateFirst,
					updateFirstTwo: updateFirstTwo,
					updateLast: updateLast,
					updateLastTwo: updateLastTwo,
					updateOthers: updateOthers
				};
			}
		
		};
		
	}])

	.service("shareData", [function() {
		var data;
		
		var addData = function(obj) {
			data = obj;
		};
		
		var getData = function() {
			return data;
		};
		
		return {
			addData: addData,
			getData: getData
		};
	}])

	.service("base64Service", [function() {
		return {
			imgToBase64: function(url, outputFormat, callback) {
			    var img = new Image();
			    img.crossOrigin = 'Anonymous';
			    img.onload = function(){
			        var canvas = document.createElement('CANVAS');
			        var ctx = canvas.getContext('2d');
			        var dataURL;
			        canvas.height = this.height;
			        canvas.width = this.width;
			        ctx.drawImage(this, 0, 0);
			        dataURL = canvas.toDataURL(outputFormat, 1);
			        callback(dataURL);
			        canvas = null; 
			    };
			    img.src = url;
			},
			
			base64ToUrl: function(base64) {
	    		var byteCharacters = atob(base64);
	    		var byteNumbers = new Array(byteCharacters.length);
	    		
	    		for (var i = 0; i < byteCharacters.length; i++) {
	    		    byteNumbers[i] = byteCharacters.charCodeAt(i);
	    		}
	    		
	    		var byteArray = new Uint8Array(byteNumbers);
	    		
	    		var urlCreator = window.URL || window.webkitURL;
	    		return urlCreator.createObjectURL( new Blob( [byteArray] , { type: "image/jpeg" }) );
			}
		}
	}])

	.service("restService", ['$http', '$sessionStorage', function($http, $sessionStorage) {
		return {
			adminController: function() {
				return {
					csrfPrevent: function() {
						return $http.get("./admin/login");
					},
					imageUpload: function(base64, capt, capb, beer, country) {
						return $http({
							method: "POST",
							url: "./rest/admin/image/upload",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							data: {baseimage: base64},
							params: { captext: capt, capbrand: capb, beer: beer, country: country }
						});
					},
					imageDelete: function(country, capId) {
						return $http({
							method: "POST",
							url: "./rest/admin/image/delete",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { country: country, capId: capId }
						});
					},
					updateCap: function(id, country, captext, capbrand, beer) {
						return $http({
							method: "POST",
							url: "./rest/admin/image/update",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { id: id, country: country, captext: captext, capbrand: capbrand, beer: beer }
						});
					},
					addNews: function(title, content) {
						return $http({
							method: "POST",
							url: "./rest/admin/news/add",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { title: title, content: content }
						});
					},
					tradeUpload: function(base64, filename) {
						return $http({
							method: "POST",
							url: "./rest/admin/trade/upload",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							data: { baseimage: base64 },
							params: { filename: filename }
						});
					},
					tradeDelete: function(ids) {
						return $http({
							method: "DELETE",
							url: "./rest/admin/tradecaps",
							headers: { 'AUTH-TOKEN': $sessionStorage.authToken },
							params: { ids: ids }
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
			photoController: function() {
				return {
					getImages: function(country) {
						return $http.get("./rest/photo/bycountry/" + country);
					},
					getNewestCaps: function(limit) {
						return $http({
							method: "GET",
							url: "./rest/photo/newest/",
							params: { limit: limit }
						});
					},
					getCountries: function() {
						return $http.get("./rest/photo/countries");
					},
					getCountryFlag: function(country) {
						return $http({
							method: "GET",
							url: "./rest/photo/flag/",
							params: { country : country }
						});
					},
					getFilteredCaps: function(country, searchText) {
						return $http({
							method: "GET",
							url: "./rest/photo/filtercap/",
							params: { country: country, searchText : searchText }
						});
					},
					getSingleCap: function(country, capId) {
						return $http({
							method: "GET",
							url: "./rest/photo/singlecap/",
							params: { country: country, id: capId }
						});
					},
					getBrand: function(id) {
						return $http({
							method: "GET",
							url: "./rest/photo/brand/",
							params: { id: id }
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
	}])

	.service("ngsrcConvertService", function () {
		return {
			convert: function (cap) {
				return cap.path + cap.file_name + "." + cap.extension.toLowerCase();
			}
		};
	});