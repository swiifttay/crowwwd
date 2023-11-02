import { BiSolidSquareRounded } from "react-icons/bi";

export function catPriceDisplay({cat, price, colour} : {cat: string, price: number, colour: string}) {
  return (
    <div className="row-span-1 flex items-center">
      <BiSolidSquareRounded className={`text-xl text-[#${colour}]`} />
      <p className="ms-2 me-20">{cat}</p>${price}
    </div>
  );
}
