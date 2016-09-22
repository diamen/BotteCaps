angular.module('bcServices')
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
						if(caps[i].entity.id - 2 <= capId && caps[i].entity.id + 2 >= capId) {
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