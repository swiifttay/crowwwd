import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

export default function EventCard() {
  return (
    <Card className="rounded-lg" >
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image="/images/chimpanzee.jpg"
          alt="green iguana"
          className="object-cover h-60"
        />
        <CardContent className=" bg-gradient-radial from-theme-blue-10 to-theme-blue-20">
          <Typography className="font-mont text-white" gutterBottom variant="h6" component="div">
            Chimpanzee
          </Typography>
          <Typography className="font-mont text-white" variant="body2" color="text.secondary">
            Lizards are a widespread group of squamate reptiles, with over 6,000
            species, ranging across all continents except Antarctica
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

//sx={{ maxWidth: 360 }}