"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// src/index.ts
const express_1 = __importDefault(require("express"));
const car_1 = __importDefault(require("./car"));
const congestionTaxCalculator_1 = require("./congestionTaxCalculator");
const app = (0, express_1.default)();
const port = 3000;
app.get('/', (req, res) => {
    res.send('Hello Volvo!');
});
app.get('/calculateTax', (req, res) => {
    // Assuming you receive vehicle details and dates from the query parameters
    const vhcle = req.query.vehicle;
    // @ts-ignore
    const dates = req.query.dates.split(',').map((dateStr) => new Date(dateStr));
    let vehicle;
    switch (vhcle) {
        case "Car":
            vehicle = new car_1.default();
            break;
        default:
            res.json({ value: 0 }); // Tax is 0
            return;
    }
    const tax = (0, congestionTaxCalculator_1.getTax)(vehicle, dates);
    res.json({ tax });
});
app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
