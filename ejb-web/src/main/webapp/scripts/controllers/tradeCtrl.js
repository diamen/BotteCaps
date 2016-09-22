angular.module('bcControllers')
	.controller('tradeCtrl', function($scope, $window, modalService, base64Service, restService, markService) {

		$scope.markedIds = [];

		function convertPhotos(data) {
			var arr = [];

			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				arr.push({src: src, id: data[i].entity.id});
			}

			return arr;
		};

		$scope.mark = function(id) {
			$scope.markedIds = markService($scope.markedIds, id);
		};

		$scope.delete = function(ids) {
			restService.adminController().deleteTrade(ids).success(function() {
				$window.location.reload();
			});
		};

		$scope.fullOpen = function(id) {
			restService.tradeController().getTradeCap(id).success(function(data) {
				var arr = convertPhotos([data]);
				var single = arr[0];
				$window.open(single.src);
			});
		};

		restService.tradeController().getMiniCaps().success(function(data) {
			$scope.files = convertPhotos(data);
		});

		$scope.openModal = function() {
			modalService.execute(function() {
				restService.adminController().deleteTrade($scope.markedIds).success(function() {
					$window.location.reload();
				});
			}, "Czy chcesz usunąć zaznaczone zdjęcia?");
		};

});