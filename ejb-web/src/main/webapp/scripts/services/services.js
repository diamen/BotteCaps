angular.module('bcServices', [])

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
					imageUpload: function(base64, capt, capb, beer, country) {
						return $http({
							method: "POST",
							url: "./rest/admin/image/upload",
							headers: {'AUTH-TOKEN': $sessionStorage.authToken },
							data: {baseimage: base64},
							params: {captext: capt, capbrand: capb, beer: beer, country: country}
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
							headers: {'AUTH-TOKEN': $sessionStorage.authToken }
							});
					}
				};
			},
			photoController: function() {
				return {
					getImages: function(country) {
						return $http.get("./rest/photo/bycountry/" + country);
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
	})

	.service("randomPhotoService", ['$http', function($http) {
		var that = {};
		
		var baseUrl = "http://localhost:8080//ejb-web//resources//gfx//";
		var ext = "jpg";
		var sep = "//";
		var _pair = {};
		var _size;
		var _countries = [];
		
		that.execute = function(size, updateView) {
			_size = size;

			$http.get("./rest/photo/countries").success(function(data) {
				_countries = data;
				
				$http.get("./rest/photo/count").success(function(pair) {
					_pair = pair;
					logic();
				});
				
			});
			
			function logic() {
				for(var i = 0; i < _size; i++) {
					var countryRandom = Math.floor(Math.random() * 1);
					var country = _countries[countryRandom];
					var capRandom = Math.floor((Math.random() * _pair[country]) + 1);
					var imgSrc = baseUrl + country + sep + capRandom + "." + ext;
					updateView(imgSrc);
				}
			};
		};
		
		return that;
	}]);