import { BiSolidSquareRounded } from "react-icons/bi";

export function CatPriceDisplay({
  cat,
  price,
  colour,
}: {
  cat: string;
  price: number;
  colour: string;
}) {
  return (
    <div className="row-span-1 flex items-center my-1 text-sm">
      <BiSolidSquareRounded className={`text-lg ${colour}`} />
      <p className="ms-2 w-10">{cat}</p>${price != 0 ? price : "No Price"}
    </div>
  );
}
