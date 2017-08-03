angular.module('createModule', ['ui.router', 'ngFileUpload'])
.controller('createController', function($scope, Upload) {
    $scope.init = function(){
        console.log("inint");
    }
    $scope.submit = function() {
          if ($scope.file) {
            $scope.upload($scope.file);
          }
    };

        // upload on file select or drop
    $scope.upload = function (file) {
        Upload.upload({
            url: 'create/new',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };
    $scope.init();
})
