angular.module('bcControllers')
	.controller('tradeCtrl', function($scope, $window, $uibModal, base64Service, restService, markService) {

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
			console.log($scope.markedIds);
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

		/* modal */
		$scope.open = function() {

			var modalInstance = $uibModal.open({
			      animation: true,
			      templateUrl: '/ejb-web/views/templates/modal.html',
			      controller: 'modalCtrl',
			      size: 'sm',
			      resolve: {
			        msg: function () {
			          return "Czy chcesz usunac zaznaczone kapsle?";
			        }
			      }
			    });

			modalInstance.result.then(function () {
			      $scope.delete($scope.markedIds);
			    }, function () {
			      console.log('dismissed');
			    });
		};
		
	});