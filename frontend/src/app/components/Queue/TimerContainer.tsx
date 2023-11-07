import { NumberBox } from "./NumberBox";

interface timeProps {
  hours: number | string;
  minutes: number | string;
  seconds: number | string;
}

export const TimerContainer = ({ hours, minutes, seconds }: timeProps) => {
  if (seconds == 0) {
    if (minutes != 0) {
      seconds = 59;
    }
  }
  if (minutes == 0) {
    if (hours != 0) {
      minutes = 59;
    }
  }

  if (hours < 10) {
    hours = "0" + hours;
  }

  if (minutes < 10) {
    minutes = "0" + minutes;
  }

  if (seconds < 10) {
    seconds = "0" + seconds;
  }

  return (
    <div className="rounded-xl">
      <div className="grid grid-cols-2 gap-4 px-10 md:flex md:items-center md:justify-between rounded-xl md:px-6  ">
        <NumberBox num={hours} unit="Hours" />
        <span className="hidden text-5xl -mt-8 md:inline-block md:text-7xl font-semibold text-[#1e1f29] ">
          :
        </span>
        <NumberBox num={minutes} unit="Minutes" />
        <span className="hidden text-5xl -mt-8 md:inline-block md:text-7xl font-semibold text-[#1e1f29] ">
          :
        </span>
        <NumberBox num={seconds} unit="Seconds" />
      </div>
    </div>
  );
};
