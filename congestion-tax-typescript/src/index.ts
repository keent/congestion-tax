// src/index.ts
import express from 'express';
import bodyParser from 'body-parser';

import Car from './car';
import {getTax} from './congestionTaxCalculator';

const app = express();
const port = 8080;

// Middleware to parse JSON and URL-encoded data in the request body
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.get('/', (req, res) => {
    res.send('Hello Volvo!');
});

app.post('/calculateTax', (req, res) => {
    // Assuming you receive vehicle details and dates from the query parameters
    const vhcle = req.body.vehicleType;
    const datesString = req.body.dates;

    // Check if req.query.dates is defined
    if (req.body.dates === undefined) {
        res.status(400).json({ error: 'Dates parameter is missing' });
        return;
    }

    const dates: Date[] = datesString.split(',').map((dateStr: string) => new Date(dateStr));

    // Check if both parameters are present
    if (!vhcle || !datesString) {
        res.status(400).json({ error: 'Missing parameters' });
        return;
    }

    let vehicle;

    switch (vhcle) {
        case "Car":
            vehicle = new Car();
            break;
        case "Emergency":
        case "Busses":
        case "Diplomat":
        case "Motorcycle":
        case "Motorbike":
        case "Military":
        case "Foreign":
            res.json({value:0});
            return;
        default:
            res.status(400).json({ error: 'Invalid vehicle type' });
            return;
    }

    const tax = getTax(vehicle, dates);


    res.json({ tax });
});

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});