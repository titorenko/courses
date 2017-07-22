function [C, sigma] = dataset3Params(X, y, Xval, yval)
%EX6PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = EX6PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 1;
sigma = 0.3;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%

%{
Cset = [0.03 0.1 0.3 1 2 3 10 30];
sigmaSet = [0.03 0.07 0.1 0.2 0.3 1 3 10 30];
allPredictions = [];

for C = Cset
  for sigma = sigmaSet
    model= svmTrain(X, y, C, @(x1, x2) gaussianKernel(x1, x2, sigma));
    predictions = svmPredict(model, Xval);
    allPredictions = [allPredictions [C; sigma; mean(double(predictions ~= yval))]];
    min(allPredictions(3,:))
  end
end
[value, index] = min(allPredictions(3,:));
allPredictions
C = allPredictions(1, index) 
sigma = allPredictions(2, index)
fprintf('Program paused. Press enter to continue.\n');
%}
C =  1;
sigma =  0.10000;

% =========================================================================

end
