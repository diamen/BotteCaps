angular.module('bcServices')
	.service("markService", [function() {

		return function(markedArr, id) {
			var markedIds = [];
			angular.copy(markedArr, markedIds);
			var index = markedIds.indexOf(id);
			if(index > -1) {
				markedIds.splice(index, 1);
				return markedIds;
			} else {
				markedIds.push(id);
				return markedIds;
			}
		};

	}]);