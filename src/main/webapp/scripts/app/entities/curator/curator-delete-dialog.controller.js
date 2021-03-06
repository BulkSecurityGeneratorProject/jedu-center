'use strict';

angular.module('jeducenterApp')
	.controller('TeacherCuratorDisableController', function($scope, $uibModalInstance, entity, CuratorRegister) {

        $scope.curator = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CuratorRegister.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
