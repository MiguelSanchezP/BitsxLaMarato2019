lat = 36 + 35*rand(200,1);
lon = 9 + 56*rand(200,1);
users = randi([0,10000000],200,1);

points = [users, lat, lon];

fran = fopen ('randomPoints.txt','w');
for i=1:200
fprintf (fran, '%i',users(i,1));
fprintf (fran, '%%');
fprintf (fran, '%f',lat(i,1));
fprintf (fran, '%%');
fprintf (fran, '%f',lon(i,1));
fprintf (fran, '\n');
endfor