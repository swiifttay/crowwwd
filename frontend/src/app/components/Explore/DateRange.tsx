import React, { useState } from 'react';
import 'react-date-range/dist/styles.css'; // main style file
import 'react-date-range/dist/theme/default.css'; // theme css file
import { DateRangePicker, Range, RangeKeyDict } from 'react-date-range';

export default function DateRange() {
  const [selectedRange, setSelectedRange] = useState<Range>({
    startDate: new Date(),
    endDate: new Date(),
    key: 'selection',
  });

  const handleSelect = (ranges: RangeKeyDict) => {
    setSelectedRange(ranges.selection);
  };

  return (
    <DateRangePicker
      ranges={[selectedRange]}
      onChange={handleSelect}
    />
  );
}