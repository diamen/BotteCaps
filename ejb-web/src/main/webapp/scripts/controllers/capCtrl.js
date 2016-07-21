angular.module('bcControllers')
	.controller('capCtrl', function($scope, $routeParams, restService, base64Service, shareData, capMover) {
		
		$scope.country = $routeParams.country;
		$scope.id = $routeParams.id;

		restService.photoController().getCountryFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});
		
		restService.photoController().getSingleCap($scope.country, $scope.id).success(function(data) {
			$scope.cap = data;
			$scope.capsrc = base64Service.base64ToUrl(data.base64);
			
			$scope.beerLabel = data.beer === 1 ? 'Piwo' : 'Niepiwo';
			
			restService.photoController().getBrand(data.brand_id).success(function(d) {
				$scope.brand = d;
			});
		});
		
		$scope.caps = shareData.getData();
		
		$scope.selectFiveCaps = function(capId) {

			var fivecaps = [];
			var capIndex = $scope.caps.map(function(e) { return e.id; }).indexOf(capId);
			
			var mover = capMover.capMover($scope.caps);
			
			if(mover.isFirst(capIndex))
				fivecaps = mover.updateFirstTwo(fivecaps);
			
			if(mover.isSecond(capIndex))
				fivecaps = mover.updateFirst(fivecaps);
			
			mover.updateOthers(fivecaps, capId);
			
			if(mover.isLast(capIndex))
				fivecaps = mover.updateLastTwo(fivecaps);
			
			if(mover.isNextToLast(capIndex))
				fivecaps = mover.updateLast(fivecaps);
			
			$scope.fivecaps = fivecaps;
			$scope.mainsrc = fivecaps[2].src;
		};
		
		$scope.selectFiveCaps(parseInt($scope.id));
		
		$scope.previousCap = function() {
			$scope.selectFiveCaps(parseInt($scope.fivecaps[1].id));
		};
		
		$scope.nextCap = function() {
			$scope.selectFiveCaps($scope.fivecaps[3].id);
		};
		
});