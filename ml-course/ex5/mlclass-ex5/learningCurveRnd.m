function [error_train, error_val] = ...
    learningCurve(X, y, Xval, yval, lambda, nSelections)
%same as learningCurve but select random examples for nSelections times 

m = size(X, 1);
mval = size(Xval, 1);

% You need to return these values correctly
error_train = zeros(m, 1);
error_val   = zeros(m, 1);

for i = 1:m
	for j = 1:nSelections
		trainSelection = randperm(m)(1:i);
		xi = [ones(i,1) X(trainSelection, :)];
		yi = y(trainSelection);

		valSelection = randperm(mval)(1:i);
		Xvali = [ones(i,1) Xval(valSelection,:)];
		yvali = yval(valSelection);
	
		[theta] = trainLinearReg(xi, yi, lambda);
		error_train(i) += linearRegCostFunction(xi, yi, theta, 0);
		error_val(i) += linearRegCostFunction(Xvali, yvali, theta, 0);
	end
	error_train(i) /= nSelections;
	error_val(i) /= nSelections;
end
