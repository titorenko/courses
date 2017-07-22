tx = ty = tz = linspace (-8, 8, 201)';
[xx, yy, zz] = meshgrid (tx, ty, tz);
v = (x.^2 + y.^2).^3 - 4 * x.^2 .* y.^2 .* (z.^2 + 1);
isosurface(x,y,z,v,0);



tx = ty = tz = linspace (-8, 8, 201)';
[x, y, z] = meshgrid (tx, ty, tz);
v = ((1+sqrt(5))^2*x.^2-4*y.^2).*((1+sqrt(5))^2*y.^2-4*z.^2).*((1+sqrt(5))^2*z.^2-4*x.^2) - 16*(2+sqrt(5))*(x.^2+y.^2+z.^2-1).^2;
[T, p, col] = isosurface(x, y, z, v, .5, y);
figure();
pa = patch('Faces',T,'Vertices',p,'FaceVertexCData',col, 'FaceColor','interp', 'EdgeColor', 'none');