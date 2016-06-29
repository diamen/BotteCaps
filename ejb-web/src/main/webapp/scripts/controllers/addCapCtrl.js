angular.module('bcControllers')
	.controller('addCapCtrl', function($scope, $routeParams, base64Service, restService) {

		$scope.country = $routeParams.country;
		
		$scope.beerOptions =
		[ {key: "Niepiwo", value: 0},
		  {key: "Piwo", value: 1} ];
		
		$scope.selected = [];
		
		$scope.uploadFiles = function(idx) {
			
			var numberOfFiles = $scope.files.length;
			var i = idx;
			
			$scope.files[i].beer = $scope.selected[i].value;
			
			base64Service.imgToBase64($scope.files[i].src, 'image/jpeg', function(base64) {
				restService.adminController().imageUpload
					(base64, $scope.files[i].captext, $scope.files[i].capbrand, $scope.files[i].beer, $scope.country)
					.success(function(data) {
						if(i + 1 < numberOfFiles) {
							i += 1;
							$scope.uploadFiles(i);
						}
					});
			});
		};
});