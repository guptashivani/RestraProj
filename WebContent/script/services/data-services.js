(function(){
	"use strict"
	
	angular
	.module('restApp')
	.service('dataService',dataServiceFn);
	
	dataServiceFn.$inject=['$q','$http'];
	function dataServiceFn($q, $http){
		var self=this;
		
		//function to get the list of seating area
		self.getSeating=function(){
			var defer=$q.defer();
			$http({
				method: 'GET',
				url: 'api/employee/all'
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};
		
		//function to see admin seating
		self.showAdminSeating=function(){
			var defer=$q.defer();
			
			$http({
				method: 'GET',
				url: 'api/employee/tables'
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};
		
		/*self.getReservation=function(code){
			var defer=$q.defer();
			
			$http({
				method: 'GET',
				url: 'api/employee/get/'+code
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};*/
		
		
		/*self.editReservation=function(){
			var defer=$q.defer();
			
			$http({
				method: 'PUT',
				url: 'api/employee/edit/'+mctrl.changeRecord.c
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};*/
		
	/*	self.addRecord=function(){
			var defer=$q.defer();
			
			$http({
				method: 'POST',
				url: 'api/employee/add',
				data: mctrl.newRecord
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};*/
		
		self.getAll=function(){
			var defer=$q.defer();
			
			$http({
				method: 'GET',
				url: 'api/employee/all/'
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};
	}
	
})();