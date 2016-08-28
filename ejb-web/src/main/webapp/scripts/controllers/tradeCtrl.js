angular.module('bcControllers')
	.controller('tradeCtrl', function($scope, $window, base64Service, restService) {

		function convertPhotos(data) {
			var arr = [];

			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				arr.push({src: src, id: data[i].id});
			}

			return arr;
		};
		
		$scope.fullOpen = function(id) {
			restService.tradeController().getTradeCap(id).success(function(data) {
				var arr = convertPhotos(new Array(data));
				var single = arr[0];
				$window.open(single.src);
			});
		};

		restService.tradeController().getMiniCaps().success(function(data) {
			$scope.files = convertPhotos(data);
		});

	});