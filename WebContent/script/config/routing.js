(function(){
	"use strict"
	
	angular.module('restApp').config(moduleConfig);
	
	moduleConfig.$inject=['$routeProvider'];
	function moduleConfig($routeProvider){
		$routeProvider.when('/view',{
			templateUrl : 'grid.html',
			controller : 'MainCtrl'
		})
		.when('/view/:code',{
			templateUrl : 'details.html',
			controller : 'MainCtrl'
		})
		.when('/edit',{
			templateUrl : 'edit.html',
			controller : 'MainCtrl'
		})
		.when('/seat',{
			templateUrl : 'seatGrid.html',
			controller : 'MainCtrl'
		})
		.otherwise({
			redirectTo: ''
		})
	}
	
	
})();